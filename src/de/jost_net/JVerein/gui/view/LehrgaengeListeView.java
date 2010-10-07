/**********************************************************************
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright (c) by Heiner Jostkleigrewe
 * All rights reserved
 * heiner@jverein.de
 * www.jverein.de
 * $Log$
 * Revision 1.3  2010-08-23 13:39:32  jost
 * Optimierung Tastatursteuerung
 *
 * Revision 1.2  2009/06/11 21:03:39  jost
 * Vorbereitung I18N
 *
 * Revision 1.1  2009/04/13 11:40:14  jost
 * Neu: Lehrg�nge
 *
 **********************************************************************/
package de.jost_net.JVerein.gui.view;

import de.jost_net.JVerein.JVereinPlugin;
import de.jost_net.JVerein.gui.action.DokumentationAction;
import de.jost_net.JVerein.gui.action.LehrgaengeListeAction;
import de.jost_net.JVerein.gui.internal.buttons.Back;
import de.jost_net.JVerein.gui.parts.LehrgaengeList;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.util.ApplicationException;

public class LehrgaengeListeView extends AbstractView
{
  public void bind() throws Exception
  {
    GUI.getView().setTitle(JVereinPlugin.getI18n().tr("Lehrg�nge"));
    new LehrgaengeList(new LehrgaengeListeAction()).getLehrgaengeList().paint(
        this.getParent());
    ButtonArea buttons = new ButtonArea(this.getParent(), 2);
    buttons.addButton(new Back(false));
    buttons.addButton(JVereinPlugin.getI18n().tr("&Hilfe"),
        new DokumentationAction(), DokumentationUtil.LEHRGANG, false,
        "help-browser.png");
  }

  public void unbind() throws ApplicationException
  {
  }

  @Override
  public String getHelp()
  {
    return "<form><p><span color=\"header\" font=\"header\">Liste der Lehrg�nge</span></p>"
        + "<p>Mit einem Rechtsklick kann ein Eintrag gel�scht werden.</p></form>";
  }
}
