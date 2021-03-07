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
import de.willuhn.jameica.gui.parts.CheckedSingleContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import eu.snoware.SnowClub.gui.action.FormularAnzeigeAction;
import eu.snoware.SnowClub.gui.action.FormularDeleteAction;
import eu.snoware.SnowClub.gui.action.FormularDuplizierenAction;
import eu.snoware.SnowClub.gui.action.FormularfelderListeAction;
import eu.snoware.SnowClub.gui.control.FormularControl;

/**
 * Kontext-Menu zu den Formularen.
 */
public class FormularMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer die Liste der Formulare.
   */
  public FormularMenu(FormularControl control)
  {
    addItem(new CheckedContextMenuItem("Formularfelder",
        new FormularfelderListeAction(), "file-invoice.png"));
    addItem(new CheckedContextMenuItem("anzeigen", new FormularAnzeigeAction(),
        "edit.png"));
    addItem(new CheckedSingleContextMenuItem("duplizieren",
        new FormularDuplizierenAction(control), "copy.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem("l�schen...", new FormularDeleteAction(),
        "trash-alt.png"));
  }
}
