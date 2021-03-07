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
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.SplitbuchungAufloesenAction;
import eu.snoware.SnowClub.gui.action.SplitbuchungNeuAction;
import eu.snoware.SnowClub.gui.control.BuchungsControl;
import eu.snoware.SnowClub.io.SplitbuchungsContainer;

public class SplitBuchungView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Splitbuchungen");

    final BuchungsControl control = new BuchungsControl(this);

    control.getSplitBuchungsList().paint(getParent());
    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.SPLITBUCHUNG, false, "question-circle.png");
    buttons.addButton("neu", new SplitbuchungNeuAction(),
        control.getCurrentObject(), false, "document-new.png");
    buttons.addButton("aufl�sen", new SplitbuchungAufloesenAction(),
        control.getCurrentObject(), false, "document-new.png");
    buttons.addButton(control.getSammelueberweisungButton());

    buttons.addButton("speichern", new Action()
    {
      @Override
      public void handleAction(Object context)
      {
        try
        {
          SplitbuchungsContainer.store();
          GUI.getStatusBar().setSuccessText("Splitbuchungen gespeichert");
        }
        catch (Exception e)
        {
          GUI.getStatusBar().setErrorText(e.getMessage());
        }
      }
    }, null, true, "save.png");
    buttons.paint(getParent());
  }
}
