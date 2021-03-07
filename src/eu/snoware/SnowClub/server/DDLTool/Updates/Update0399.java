/**********************************************************************
 * JVerein - Mitgliederverwaltung und einfache Buchhaltung f�r Vereine
 * Copyright (c) by Heiner Jostkleigrewe
 * Copyright (c) 2015 by Thomas Hooge
 * Main Project: heiner@jverein.dem  http://www.jverein.de/
 * Module Author: thomas@hoogi.de, http://www.hoogi.de/
 *
 * This file is part of JVerein.
 *
 * JVerein is free software: you can redistribute it and/or modify 
 * it under the terms of the  GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JVerein is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **********************************************************************/
package eu.snoware.SnowClub.server.DDLTool.Updates;

import java.sql.Connection;

import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;
import eu.snoware.SnowClub.server.DDLTool.AbstractDDLUpdate;
import eu.snoware.SnowClub.server.DDLTool.Column;
import eu.snoware.SnowClub.server.DDLTool.Index;

public class Update0399 extends AbstractDDLUpdate
{
  public Update0399(String driver, ProgressMonitor monitor, Connection conn)
  {
    super(driver, monitor, conn);
  }

  @Override
  public void run() throws ApplicationException
  {
    // Buchungsart in das Mitgliedskonto aufnehmen
    execute(addColumn("mitgliedskonto",
        new Column("buchungsart", COLTYPE.BIGINT, 0, null, false, false)));

    Index idx = new Index("ixMitgliedskonto3", false);
    Column col = new Column("buchungsart", COLTYPE.BIGINT, 0, null, false,
        false);
    idx.add(col);
    execute(idx.getCreateIndex("mitgliedskonto"));

    execute(createForeignKey("fkMitgliedskonto3", "mitgliedskonto",
        "buchungsart", "buchungsart", "id", "RESTRICT", "NO ACTION"));

  }
}