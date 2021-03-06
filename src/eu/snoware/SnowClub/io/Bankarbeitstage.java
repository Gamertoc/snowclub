/**********************************************************************
 * Copyright (c) by Heiner Jostkleigrewe
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 *  the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, 
 * see <http://www.gnu.org/licenses/>.
 * 
 * heiner@jverein.de
 * www.jverein.de
 **********************************************************************/
package eu.snoware.SnowClub.io;

import java.util.Calendar;

import de.jollyday.HolidayManager;

public class Bankarbeitstage
{
  private HolidayManager m;

  public Bankarbeitstage()
  {
    m = HolidayManager.getInstance("Bankfeiertage");
  }

  public Calendar getCalendar(Calendar from, int anzahl)
  {
    for (int i = 0; i < anzahl; i++)
    {
      from.add(Calendar.DAY_OF_YEAR, 1);
      while (from.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
          || from.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
          || m.isHoliday(from, ""))
      {
        from.add(Calendar.DAY_OF_YEAR, 1);
      }
    }
    return from;
  }
}
