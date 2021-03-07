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

import java.io.IOException;

import de.willuhn.datasource.GenericIterator;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.control.MitgliedskontoControl;
import eu.snoware.SnowClub.keys.FormularArt;
import eu.snoware.SnowClub.rmi.Formular;
import eu.snoware.SnowClub.rmi.Mitgliedskonto;

public class Mahnungsausgabe extends AbstractMitgliedskontoDokument
{

  public Mahnungsausgabe(MitgliedskontoControl control) throws IOException
  {
    super(control, MitgliedskontoControl.TYP.MAHNUNG);

    Formular form = (Formular) control.getFormular(FormularArt.MAHNUNG)
        .getValue();
    if (form == null)
    {
      throw new IOException("kein Mahnungsformular ausgewählt");
    }
    Formular formular = (Formular) Einstellungen.getDBService().createObject(
        Formular.class, form.getID());

    // Wurde ein Object übergeben?
    if (control.getCurrentObject() != null)
    {
      // Ja: Einzeldruck aus dem Kontextmenu
      mks = getRechnungsempfaenger(control.getCurrentObject());
    }
    else
    {
      GenericIterator it = control.getMitgliedskontoIterator();
      Mitgliedskonto[] mk = new Mitgliedskonto[it.size()];
      int i = 0;
      while (it.hasNext())
      {
        mk[i] = (Mitgliedskonto) it.next();
        i++;
      }
      mks = getRechnungsempfaenger(mk);
    }
    aufbereitung(formular);
  }
}
