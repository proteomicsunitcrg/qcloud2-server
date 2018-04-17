package eu.qcloud.data.processor.processors;

import java.util.List;

import eu.qcloud.data.DataForPlot;

public class Log2Processor extends Processor {

	@Override
	public void setData(List<DataForPlot> data) {
		// TODO Auto-generated method stub
		super.setData(data);
	}

	@Override
	public List<DataForPlot> processData() {
		// TODO Auto-generated method stub
		
		for(DataForPlot d: this.data) {
			float value = d.getValue();
			
			if(value>0) {
				Float log2 = log2(value); 
				d.setValue(log2);
			}else {
				d.setValue(Float.NaN);
			}
		}
		return this.data;
	}
	
	

	@Override
	public String toString() {
		
		return "log2";
	}

	@Override
	public boolean isGuideSetRequired() {
		return false;
	}

	public static final float log2(float f)
	{
	    return (float) (Math.log(f)/Math.log(2.0));
	}
    public static int log2nlz( int bits )
    {
        if( bits == 0 )
            return 0; // or throw exception
        return 31 - Integer.numberOfLeadingZeros( bits );
    }
}
