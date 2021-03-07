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
package eu.snoware.SnowClub.gui.view;

import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.MitgliedNextBGruppeControl;

/**
 * @author Rolf Mamat
 */
public class MitgliedNextBGruppeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    MitgliedNextBGruppeControl control = new MitgliedNextBGruppeControl(this);

    LabelGroup lblGroup = new LabelGroup(getParent(),
        "neue Beitragsgruppe f�r Zukunft");
    lblGroup.addLabelPair("f�r Mitglied", control.getMitgliedsnameInput());
    lblGroup.addLabelPair("aktuelle Beitragsgruppe",
        control.getBeitragsgruppeAktuellInput());
    lblGroup.addLabelPair("Ab Datum", control.getAbDatumInput());
    lblGroup.addLabelPair("neue Beitragsgruppe",
        control.getBeitragsgruppeInput());
    lblGroup.addLabelPair("Bemerkung", control.getBemerkungsInput());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.MITGLIED, false, "question-circle.png");
    buttons.addButton("Speichern", control.getSpeichernAction(), null, false,
        "save.png");
    buttons.paint(getParent());
  }
}
