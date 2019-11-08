package eu.qcloud.intranet.node;

public class LSStats {

    private Long totalFiles;
    
    private Long filesLastMonths;

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public Long getFilesLastMonths() {
        return filesLastMonths;
    }

    public void setFilesLastMonths(Long filesLast6Months) {
        this.filesLastMonths = filesLast6Months;
    }

    public LSStats(Long totalFiles, Long filesLast6Months) {
        this.totalFiles = totalFiles;
        this.filesLastMonths = filesLast6Months;
    }

    public LSStats() {
    }
    
    
}