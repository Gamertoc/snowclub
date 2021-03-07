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

import java.rmi.RemoteException;
import java.sql.SQLException;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.dialogs.EigenschaftenAuswahlDialog;
import eu.snoware.SnowClub.gui.dialogs.EigenschaftenAuswahlParameter;
import eu.snoware.SnowClub.rmi.Eigenschaften;
import eu.snoware.SnowClub.rmi.Mitglied;
import eu.snoware.SnowClub.server.EigenschaftenNode;

/**
 * Eigenschaften an Mitglieder zuordnen.
 */
public class MitgliedEigenschaftZuordnungAction implements Action
{

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null
        || (!(context instanceof Mitglied) && !(context instanceof Mitglied[])))
    {
      throw new ApplicationException("Kein Mitglied ausgewählt");
    }
    Mitglied[] m = null;
    if (context instanceof Mitglied)
    {
      m = new Mitglied[] { (Mitglied) context };
    }
    else if (context instanceof Mitglied[])
    {
      m = (Mitglied[]) context;
    }
    int anzErfolgreich = 0;
    int anzBereitsVorhanden = 0;
    try
    {
      EigenschaftenAuswahlDialog ead = new EigenschaftenAuswahlDialog("", true,
          false);
      EigenschaftenAuswahlParameter param = ead.open();
      for (EigenschaftenNode en : param.getEigenschaften())
      {
        for (Mitglied mit : m)
        {
          Eigenschaften eig = (Eigenschaften) Einstellungen.getDBService()
              .createObject(Eigenschaften.class, null);
          eig.setEigenschaft(en.getEigenschaft().getID());
          eig.setMitglied(mit.getID());
          try
          {
            eig.store();
            anzErfolgreich++;
          }
          catch (RemoteException e)
          {
            if (e.getCause() instanceof SQLException)
            {
              anzBereitsVorhanden++;
            }
            else
            {
              throw new ApplicationException(e);
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      Logger.error(

      "Fehler beim Anlegen neuer Eigenschaften", e);
      return;
    }
    GUI.getStatusBar().setSuccessText(
        String.format(
            "%d Eigenschaft(en) angelegt. %d waren bereits vorhanden.",
            anzErfolgreich, anzBereitsVorhanden));
  }
}
