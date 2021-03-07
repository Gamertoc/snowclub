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
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.SimpleContainer;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.FormularAnzeigeAction;
import eu.snoware.SnowClub.gui.action.FormularfeldAction;
import eu.snoware.SnowClub.gui.action.FormularfelderExportAction;
import eu.snoware.SnowClub.gui.action.FormularfelderImportAction;
import eu.snoware.SnowClub.gui.control.FormularfeldControl;
import eu.snoware.SnowClub.rmi.Formular;

public class FormularfelderListeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Formularfelder");

    FormularfeldControl control = new FormularfeldControl(this,
        (Formular) getCurrentObject());
    SimpleContainer labelContainer = new SimpleContainer(getParent(), false, 4);
    labelContainer.addLabelPair("Formulartyp:", control.getFormularTyp());
    labelContainer.addLabelPair("Formularname:", control.getFormularName());

    control.getFormularfeldList().paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.FORMULARE, false, "question-circle.png");
    buttons.addButton("Export", new FormularfelderExportAction(),
        getCurrentObject(), false, "save.png");
    buttons.addButton("Import", new FormularfelderImportAction(control),
        getCurrentObject(), false, "file-import.png");
    buttons.addButton("anzeigen", new FormularAnzeigeAction(),
        getCurrentObject(), false, "edit.png");
    buttons.addButton("neu", new FormularfeldAction(), getCurrentObject(),
        false, "document-new.png");
    buttons.paint(this.getParent());
  }
}
