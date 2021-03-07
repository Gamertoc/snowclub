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
import de.willuhn.jameica.gui.util.ScrolledContainer;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.EinstellungControl;

public class EinstellungenMailView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Einstellungen Mail");

    final EinstellungControl control = new EinstellungControl(this);

    ScrolledContainer cont = new ScrolledContainer(getParent());

    cont.addLabelPair("Server", control.getSmtpServer());
    cont.addLabelPair("Port", control.getSmtpPort());
    cont.addLabelPair("Benutzer", control.getSmtpAuthUser());
    cont.addLabelPair("Passwort", control.getSmtpAuthPwd());
    cont.addLabelPair("Absenderadresse", control.getSmtpFromAddress());
    cont.addLabelPair("Anzeigename", control.getSmtpFromAnzeigename());
    cont.addLabelPair("SSL verwenden", control.getSmtpSsl());
    cont.addLabelPair("StartTLS verwenden", control.getSmtpStarttls());
    cont.addLabelPair("Pause zwischen Mailversand in Millisek.",
        control.getMailVerzoegerung());
    cont.addLabelPair("Immer Cc an Adresse", control.getAlwaysCcTo());
    cont.addLabelPair("Immer Bcc an Adresse", control.getAlwaysBccTo());

    cont.addSeparator();
    cont.addText("IMAP 'Gesendete'-Ordner", false);
    cont.addLabelPair("Kopie in 'Gesendete'-Ordner IMAP ablegen",
        control.getCopyToImapFolder());
    cont.addLabelPair("IMAP Server", control.getImapHost());
    cont.addLabelPair("IMAP Port", control.getImapPort());
    cont.addLabelPair("IMAP Benutzer", control.getImapAuthUser());
    cont.addLabelPair("IMAP Passwort", control.getImapAuthPwd());
    cont.addLabelPair("IMAP SSL verwenden", control.getImap_ssl());
    cont.addLabelPair("IMAP StartTLS verwenden", control.getImap_starttls());
    cont.addLabelPair("IMAP 'Gesendete'-Ordername",
        control.getImapSentFolder());
    cont.addSeparator();
    cont.addLabelPair("Signatur", control.getMailSignatur());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.EINSTELLUNGEN, false, "question-circle.png");
    buttons.addButton("speichern", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        control.handleStoreMail();
      }
    }, null, true, "save.png");
    buttons.paint(this.getParent());
  }
}
