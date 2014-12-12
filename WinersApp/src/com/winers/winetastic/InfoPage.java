package com.winers.winetastic;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/** 
 * Info page template for displaying statistics and information.
 * 
 * @author Victoria Do
 * @see WineInfoPage
 *
 */

public class InfoPage extends AbstractActivity {
	
	protected  TableLayout  statsTable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_wine_info_page, menu);
        return true;
    }
    
    /**
     * Attaches a row to a TableLayout.
     * 
     * @param parent  the TableLayout view to attach the row to
     * @param cols    text to be added to the row
     */
    protected void addRow(View parent, String ... cols) {
    	// Create a table row
    	TableRow r = (TableRow) getLayoutInflater().inflate(R.layout.info_row, null);
    	
    	for(int i = 0; i < cols.length; i++) {
    		
    		// Add each string to the row
    		TextView text = new TextView(this);
    		
    		// Bold the first string
    		if(i == 0 && cols.length > 1) {
    			text.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
    			text.setWidth(120);
    		}
    		
    		// Set contents of text, make black, change width
    		text.setText(cols[i], TextView.BufferType.NORMAL);
    		Linkify.addLinks(text, Linkify.ALL);
    		text.setTextColor(getResources().getColor(R.color.black));
    		Linkify.addLinks(text, Linkify.ALL);   		
    		
    		r.addView(text);
    	}    	
    	((TableLayout)parent).addView(r);
    }
    
    protected String parseString(String s, int maxLineLength, boolean breakWords) {
    	System.err.println(s);
    	if(s.length() < maxLineLength) return s;
    	for(int i=0; i<s.length(); i++) {
    		if(i % maxLineLength == 0) {
    			int last = i;
    			while(i >= 0 && s.charAt(i) != ' ')  i--;
				s = s.substring(0,i+1)  
						+ System.getProperty("line.separator")
						+ s.substring(i+1);
    			i = last + 3;
    		}
    	}
    	return s;
    }
    
	@Override
	protected int getTitleText() {
		return R.string.info_title;
	}
}
