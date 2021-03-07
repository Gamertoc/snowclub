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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import eu.snoware.SnowClub.io.Adressbuch.Adressaufbereitung;
import eu.snoware.SnowClub.rmi.Mitglied;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.Gender;
import ezvcard.property.Kind;
import ezvcard.property.Revision;
import ezvcard.property.StructuredName;
import ezvcard.property.Uid;

public class VCardTool
{

  public static VCard[] getVCards(ArrayList<Mitglied> mitglieder)
      throws RemoteException
  {
    ArrayList<VCard> ret = new ArrayList<>();

    for (Mitglied m : mitglieder)
    {
      VCard vcard = new VCard();

      vcard.setKind(Kind.individual());

      Calendar cal = Calendar.getInstance();
      cal.setTime(m.getGeburtsdatum());
      if (cal.get(Calendar.YEAR) != 1900
          && cal.get(Calendar.MONTH) != Calendar.JANUARY)
      {
        Birthday bd = new Birthday(m.getGeburtsdatum());
        vcard.setBirthday(bd);
      }

      if (m.getGeschlecht().equalsIgnoreCase("M"))
      {
        vcard.setGender(Gender.male());
      }
      if (m.getGeschlecht().equalsIgnoreCase("W"))
      {
        vcard.setGender(Gender.female());
      }
      if (m.getGeschlecht().equalsIgnoreCase("o"))
      {
        vcard.setGender(Gender.other());
      }
      if (m.getPersonenart().equals("n"))
      {
        StructuredName n = new StructuredName();
        n.setFamily(m.getName());
        n.setGiven(m.getVorname());
        // n.addPrefix("Mr");
        vcard.setStructuredName(n);
      }

      vcard.setFormattedName(Adressaufbereitung.getVornameName(m));

      Address adr = new Address();
      adr.setStreetAddress(m.getStrasse());
      adr.setLocality(m.getOrt());
      adr.setPostalCode(m.getPlz());
      adr.setCountry(m.getStaat());
      adr.setLabel(Adressaufbereitung.getAdressfeld(m));
      adr.getTypes().add(AddressType.HOME);
      vcard.addAddress(adr);

      if (m.getTelefondienstlich().length() > 0)
      {
        vcard.addTelephoneNumber(m.getTelefondienstlich(), TelephoneType.WORK);
      }
      if (m.getTelefonprivat().length() > 0)
      {
        vcard.addTelephoneNumber(m.getTelefonprivat(), TelephoneType.HOME);
      }
      if (m.getHandy().length() > 0)
      {
        vcard.addTelephoneNumber(m.getHandy(), TelephoneType.CELL);
      }

      if (m.getEmail().length() > 0)
      {
        vcard.addEmail(m.getEmail(), EmailType.HOME);
      }
      // vcard.setCategories("widgetphile", "biker", "vCard expert");

      Uid uid = new Uid(m.getID());
      vcard.setUid(uid);
      vcard.setRevision(Revision.now());

      ret.add(vcard);
    }
    VCard list2[] = new VCard[ret.size()];
    list2 = ret.toArray(list2);
    return list2;
  }
}
