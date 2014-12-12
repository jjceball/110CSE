package com.winers.winetastic;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class EventCalendar extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        CalendarView calendarView=(CalendarView) findViewById(R.id.e_cal_home);
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
            	//boolean eventExists = false;
            	int id = (year * 10000) + (month * 100) + dayOfMonth;
            	boolean isEvent = false;
            	String title = "";
            	String place = "";
            	String time = "";
            	String what = "";
            	String print = "";
                //String exists = "SELECT count(*) as num FROM events WHERE Event_ID = " + id;
                //Cursor c = db.rawQuery(exists, null);
                //c.moveToFirst();
                if(id == 20130417)
                {
                	title = "Sun God";
                	place = "UCSD";
                	time = "2:00pm - 11:00pm";
                	what = "Indulge in activities you don't normally do, and do so with excess."
                			+" Please leave your school work and dignity "
                			+ "at the entrance, cuz its gonna get cray cray!!!";
                	isEvent = true;
                }
                else if(id == 20130501 || id == 20130502 || id==20130430 || id==20130431)
                {
                	title = "Annual Auction";
                	place = "Napa Valley Convention Center";
                	time = "11:00am - 7:00pm";
                	what = "You are cordially invited to one of the most extraordinary wine events the world over, where wine lover and winemaker meet at the source of America's legendary wines to partake in and celebrate the best Napa Valley has to offer!";
                	isEvent = true;
                }
                else if(id == 20130515 ||id == 20130516 ||id == 20130517)
                {
                	title = "Taste of Howell Mountain";
                	place = "Charles Krug Winery, 2800 Main Street, St. Helena";
                	time = "12:00pm - 3:00pm";
                	what = "Indulge yourself in wines from 42 Howell Mountain wineries and gourmet cuisine from Winery Chefs. Bid and win silent and live auctions!";
                	isEvent = true;
                }
                else if(id == 20130505 || id == 20130506 || id == 20130507 || id == 20130508 || id == 20130509)
                {
                	title = "Brian Culbertson's Jazz Getaway";
                	place = "See Website";
                	time = "9:00am - 11:00pm";
                	what = "The 2nd annual jazz and wine festival hosted by contemporary jazz star Brian Culbertson is back with added featured artists and more events!";
                	isEvent = true;
                }
                else if(id == 20130523 || id == 20130522)
                {
                	title = "Live Music";
                	place = "Beringer Winery";
                	time = "12:00pm - 5:00pm";
                	what = "Enjoy live music while exploring the beautiful and historic grounds of Beringer! Every Saturday and Sunday through October you can delight in sounds from local bands while nibbling on savory bites and sipping delicious wine. Plus, spend a glorious afternoon at one of Napa’s oldest wineries!";
                	isEvent = true;
                }
                else
                	//Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();

                	Toast.makeText(getApplicationContext(), "No event on this day", Toast.LENGTH_SHORT).show();
                
                if(isEvent)
                {
                	print = /*title + "\n" + */"Where: " + place + "\n"
                			+ "When: " + time + "\n"
                			+ "What: " + what + "\n";
                	TextView titleView = new TextView(EventCalendar.this);
                	titleView.setGravity(Gravity.CENTER);
                	titleView.setText(title);
                	titleView.setTextColor(getResources().getColor(R.color.cream));
                	titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP , 25);
                	new AlertDialog.Builder(new ContextThemeWrapper(EventCalendar.this, R.style.AlertDialogCustom))
                    .setCustomTitle(titleView)
                    .setMessage(print)
                    .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { 
                        }
                     })
                     .show();
                }
                

            }
        });
    }
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.title_activity_calendar;
	}
	
	
	

}
