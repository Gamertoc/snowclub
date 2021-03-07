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
import eu.snoware.SnowClub.gui.action.DokumentDeleteAction;
import eu.snoware.SnowClub.gui.action.DokumentInfoBearbeitenAction;
import eu.snoware.SnowClub.gui.action.DokumentShowAction;

/**
 * Kontext-Menu zu den Dokumenten.
 */
public class DokumentMenu extends ContextMenu
{

  public DokumentMenu(boolean enabled)
  {
    new ContextMenuItem();
    addItem(new CheckedContextMenuItem("anzeigen", new DokumentShowAction(),
        "eye.png"));
    addItem(new CheckedContextMenuItem("Infos bearbeiten",
        new DokumentInfoBearbeitenAction(), "edit.png"));
    if (enabled)
    {
      addItem(ContextMenuItem.SEPARATOR);
      addItem(new CheckedContextMenuItem("l�schen...",
          new DokumentDeleteAction(), "trash-alt.png"));
    }
  }
}
