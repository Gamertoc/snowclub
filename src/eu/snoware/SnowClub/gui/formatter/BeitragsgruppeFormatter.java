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
package eu.snoware.SnowClub.gui.formatter;

import java.rmi.RemoteException;

import de.willuhn.jameica.gui.formatter.Formatter;
import eu.snoware.SnowClub.rmi.Beitragsgruppe;

public class BeitragsgruppeFormatter implements Formatter
{
  @Override
  public String format(Object o)
  {
    Beitragsgruppe beitragsgruppe = (Beitragsgruppe) o;
    try
    {
      return beitragsgruppe.getBezeichnung();
    }
    catch (RemoteException e)
    {
      return e.getMessage();
    }
  }
}
