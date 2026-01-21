# Peptide Sequence Underscore Mismatch Fix

## Problem Summary

The qCloud2 system was not receiving data for monitored peptides despite the Nextflow pipeline successfully processing and sending the data. This was caused by a **peptide sequence format mismatch** between the database and the incoming pipeline data.

### Root Cause

- **Database**: Monitored peptides stored with trailing underscore (e.g., `HLVDEPQNLIK_`)
- **Pipeline Data**: Peptide sequences without trailing underscore (e.g., `HLVDEPQNLIK`)
- **Matching Logic**: Used exact string matching via `peptideRepository.findBySequence()`, which failed when formats didn't match

### Impact

When the pipeline sends data with peptide sequence `HLVDEPQNLIK`, the system looks for an exact match in the database. Since the database has `HLVDEPQNLIK_` (with underscore), no match is found, and the data is silently skipped:

```java
case "Peptide":
    cs = peptideRepository.findBySequence(dataValue.getContextSource());  // Returns null
    if (cs == null) {
        continue;  // Data skipped!
    }
```

## Solution

Implemented **flexible peptide sequence matching** by modifying the existing `findBySequence()` method to automatically handle both formats.

### Changes Made

#### Modified `PeptideRepository.java` (ONLY file changed)

**Key Changes**:
1. Renamed the original JPA query method to `findBySequenceExact()`
2. Made `findBySequence()` a `default` method with flexible matching logic
3. All existing code continues to work without any modifications

```java
/**
 * Direct database query for peptide by exact sequence match
 * Used internally by findBySequence() - do not call directly
 */
@Query("select p from peptide p where p.sequence = ?1")
Peptide findBySequenceExact(String sequence);

/**
 * Find peptide by sequence with flexible underscore handling.
 * This method tries to match the sequence as-is first, then tries
 * with/without trailing underscore to handle format inconsistencies
 * between the database and incoming data.
 * 
 * @param sequence The peptide sequence to search for
 * @return Peptide if found (with or without trailing underscore), null otherwise
 */
default Peptide findBySequence(String sequence) {
    // Try exact match first
    Peptide peptide = findBySequenceExact(sequence);
    
    // If not found and sequence doesn't end with underscore, try adding one
    if (peptide == null && !sequence.endsWith("_")) {
        peptide = findBySequenceExact(sequence + "_");
    } 
    // If not found and sequence ends with underscore, try without it
    else if (peptide == null && sequence.endsWith("_")) {
        peptide = findBySequenceExact(sequence.substring(0, sequence.length() - 1));
    }
    
    return peptide;
}
```

**Logic**:
- First tries exact match using `findBySequenceExact()`
- If no match and sequence doesn't end with `_`, try adding `_`
- If no match and sequence ends with `_`, try removing it
- Returns `null` only if both attempts fail

**Why This Approach is Better**:
- ✅ **Zero changes to `DataService.java`** - All existing calls to `findBySequence()` automatically get the new behavior
- ✅ **Single point of change** - Only one file modified
- ✅ **Transparent upgrade** - No code outside `PeptideRepository` needs to know about this change
- ✅ **Consistent behavior** - Every place that calls `findBySequence()` gets the same flexible matching

## Benefits

### ✅ Minimal Code Changes
- **Only 1 file modified**: `PeptideRepository.java`
- **Zero changes to business logic**: `DataService.java` unchanged
- All existing calls to `findBySequence()` automatically benefit

### ✅ Backward Compatible
- Works with both underscore and non-underscore formats
- No database migration required
- No pipeline changes needed
- Existing code continues to work exactly as before

### ✅ Performance Efficient
- Only one additional database query when formats don't match
- Most queries will succeed on first attempt (exact match)
- No performance impact on existing matched data

### ✅ Future-Proof
- Handles legacy data with underscores
- Handles new data without underscores
- Maintains consistency regardless of format inconsistencies
- Single point of maintenance for all peptide lookups

## Testing Recommendations

1. **Test with underscore peptides**: Verify data ingestion works for peptides stored as `SEQUENCE_`
2. **Test without underscore peptides**: Verify data ingestion works for peptides stored as `SEQUENCE`
3. **Test mixed environment**: Verify system handles both formats simultaneously
4. **Monitor logs**: Check for any peptide sequence matching issues in production logs
5. **Verify thresholds**: Ensure threshold evaluations work correctly with both formats
6. **Performance testing**: Confirm no significant performance degradation

## Example Scenario

**Before Fix**:
```
Database Peptide: HLVDEPQNLIK_
Pipeline sends:   HLVDEPQNLIK
Result:           No match → Data skipped ❌
```

**After Fix**:
```
Database Peptide: HLVDEPQNLIK_
Pipeline sends:   HLVDEPQNLIK
Result:           Flexible match succeeds → Data inserted ✅

Search #1: findBySequenceExact("HLVDEPQNLIK") → null
Search #2: findBySequenceExact("HLVDEPQNLIK_") → Found! ✅
```

## Files Modified

1. `src/main/java/eu/qcloud/contextSource/peptide/PeptideRepository.java`
   - Added `findBySequenceExact()` method with `@Query` annotation
   - Converted `findBySequence()` to a `default` method with flexible matching

**Total files changed: 1**

## Deployment Notes

- **No database changes required**
- **No configuration changes required**
- **No pipeline changes required**
- **No changes to DataService or any other business logic**
- Simple code deployment
- Can be deployed during normal maintenance window
- Immediate effect on all peptide lookups system-wide

## Impact Analysis

### Code Locations Automatically Fixed

All these locations now automatically benefit from flexible matching without any code changes:

1. **DataService.java**:
   - `insertDataFromPipeline()` - Primary data insertion from pipeline
   - `getContextSourceFromDatabase()` - Threshold evaluation
   - `peptideBelongsToSampleType()` - Sample type validation

2. **PeptideService.java**:
   - `updatePeptide()` - Peptide updates
   - `getPeptideBySequence()` - Peptide retrieval
   - `findPeptideBySequence()` - Peptide search

3. **Any other code** that calls `peptideRepository.findBySequence()`

## Related Issues

This fix resolves the core issue where monitored peptides were not receiving data from the Nextflow pipeline due to sequence format mismatches. The solution maintains full backward compatibility while supporting both naming conventions, and does so with minimal code changes (only 1 file modified).

## Technical Notes

### Why Use `default` Methods?

Java 8+ allows interfaces to have `default` methods with implementation. This feature is perfect for our use case because:

1. **Backward Compatibility**: Existing implementations don't break
2. **Single Point of Change**: Logic centralized in the interface
3. **Automatic Propagation**: All callers get the new behavior instantly
4. **No Inheritance Issues**: Works with Spring Data JPA repositories

### Alternative Approaches Considered

1. ❌ **Modify DataService.java**: Would require changes in multiple locations
2. ❌ **Database migration**: Would require complex data migration and potential downtime
3. ❌ **Pipeline changes**: Would require changes to external system
4. ✅ **Interface default method**: Single point of change, zero impact on callers

## Summary

This elegant solution fixes the peptide sequence mismatch issue by modifying only the `PeptideRepository` interface, allowing all existing code to automatically benefit from flexible underscore handling without any changes. The fix is minimal, transparent, backward compatible, and immediately effective across the entire system.
