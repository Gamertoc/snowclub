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
package eu.snoware.SnowClub.gui.menu;

import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import eu.snoware.SnowClub.gui.action.MitgliedNextBGruppeLoeschenAction;
import eu.snoware.SnowClub.gui.action.MitgliedNextBGruppeNeuAction;
import eu.snoware.SnowClub.gui.control.MitgliedControl;

/**
 * @author Rolf Mamat
 */
public class MitgliedNextBGruppeMenue extends ContextMenu
{
  public MitgliedNextBGruppeMenue(MitgliedControl control)
  {
    addItem(new ContextMenuItem("Beitragsgruppe hinzufügen",
        new MitgliedNextBGruppeNeuAction(control), "file.png"));
    addItem(new CheckedContextMenuItem("Beitragsgruppe löschen",
        new MitgliedNextBGruppeLoeschenAction(), "trash-alt.png"));
  }
}
