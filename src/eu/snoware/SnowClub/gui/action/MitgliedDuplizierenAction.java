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
package eu.snoware.SnowClub.gui.action;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.gui.view.AdresseDetailView;
import eu.snoware.SnowClub.gui.view.MitgliedDetailView;
import eu.snoware.SnowClub.rmi.Mitglied;

public class MitgliedDuplizierenAction implements Action
{

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null || !(context instanceof Mitglied))
    {
      throw new ApplicationException("kein Mitglied ausgewählt");
    }
    Mitglied m = null;
    try
    {
      m = (Mitglied) context;
      m.setID(null);
      if (m.getAdresstyp().getJVereinid() == 1)
      {
        GUI.startView(new MitgliedDetailView(), m);
      }
      else
      {
        GUI.startView(new AdresseDetailView(), m);
      }
    }
    catch (Exception e)
    {
      throw new ApplicationException(
          "Fehler beim duplizieren eines Mitgliedes", e);
    }
  }
}
