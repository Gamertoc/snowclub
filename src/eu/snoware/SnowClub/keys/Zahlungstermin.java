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
package eu.snoware.SnowClub.keys;

/**
 * Zahlungstermine
 */

public enum Zahlungstermin
{

  MONATLICH(1, "monatlich", new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }), //
  VIERTELJAEHRLICH1(31, "viertelj?hrlich (Jan./Apr./Juli/Okt)", //
      new int[] { 1, 4, 7, 10 }), //
  VIERTELJAEHRLICH2(32, "viertelj?hrlich (Feb./Mai /Aug./Nov.)", //
      new int[] { 2, 5, 8, 11 }), //
  VIERTELJAEHRLICH3(33, "viertelj?hrlich (M?rz/Juni/Sep./Dez.)", //
      new int[] { 3, 6, 9, 12 }), //
  HALBJAEHRLICH1(61, "halbj?hrlich (Jan./Juli)", new int[] { 1, 7 }), //
  HALBJAEHRLICH2(62, "halbj?hrlich (Feb./Aug.)", new int[] { 2, 8 }), //
  HALBJAEHRLICH3(63, "halbj?hrlich (M?rz/Sep.)", new int[] { 3, 9 }), //
  HALBJAEHRLICH4(64, "halbj?hrlich (Apr./Okt.)", new int[] { 4, 10 }), //
  HALBJAEHRLICH5(65, "halbj?hrlich (Mai /Nov.)", new int[] { 5, 11 }), //
  HALBJAEHRLICH6(66, "halbj?hrlich (Juni/Dez.)", new int[] { 6, 12 }), //
  JAERHLICH01(1201, "j?hrlich (Jan.)", new int[] { 1 }), //
  JAERHLICH02(1202, "j?hrlich (Feb.)", new int[] { 2 }), //
  JAERHLICH03(1203, "j?hrlich (M?rz)", new int[] { 3 }), //
  JAERHLICH04(1204, "j?hrlich (Apr.)", new int[] { 4 }), //
  JAERHLICH05(1205, "j?hrlich (Mai )", new int[] { 5 }), //
  JAERHLICH06(1206, "j?hrlich (Juni)", new int[] { 6 }), //
  JAERHLICH07(1207, "j?hrlich (Juli)", new int[] { 7 }), //
  JAERHLICH08(1208, "j?hrlich (Aug.)", new int[] { 8 }), //
  JAERHLICH09(1209, "j?hrlich (Sep.)", new int[] { 9 }), //
  JAERHLICH10(1210, "j?hrlich (Okt.)", new int[] { 10 }), //
  JAERHLICH11(1211, "j?hrlich (Nov.)", new int[] { 11 }), //
  JAERHLICH12(1212, "j?hrlich (Dez.)", new int[] { 12 });

  private final String text;

  private final int key;

  private int[] monate;

  Zahlungstermin(int key, String text, int[] monate)
  {
    this.key = key;
    this.text = text;
    this.monate = monate;
  }

  public int getKey()
  {
    return key;
  }

  public String getText()
  {
    return text;
  }

  public static Zahlungstermin getByKey(int key)
  {
    for (Zahlungstermin zt : Zahlungstermin.values())
    {
      if (zt.getKey() == key)
      {
        return zt;
      }
    }
    return null;
  }

  // public static ArrayList<Zahlungstermin> getZahlungstermin(int monat)
  // {
  // ArrayList<Zahlungstermin> ret = new ArrayList<Zahlungstermin>();
  // for (Zahlungstermin t : Zahlungstermin.values())
  // {
  // for (int m : t.monate)
  // {
  // if (m == monat)
  // {
  // ret.add(t);
  // }
  // }
  // }
  // return ret;
  // }
  public boolean isAbzurechnen(int monat)
  {
    for (int m : monate)
    {
      if (monat == m)
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString()
  {
    return getText();
  }
}
