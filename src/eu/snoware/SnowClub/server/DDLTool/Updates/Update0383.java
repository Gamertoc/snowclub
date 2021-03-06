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
package eu.snoware.SnowClub.server.DDLTool.Updates;

import java.sql.Connection;

import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;
import eu.snoware.SnowClub.server.DDLTool.AbstractDDLUpdate;
import eu.snoware.SnowClub.server.DDLTool.Column;
import eu.snoware.SnowClub.server.DDLTool.Index;

public class Update0383 extends AbstractDDLUpdate
{
  public Update0383(String driver, ProgressMonitor monitor, Connection conn)
  {
    super(driver, monitor, conn);
  }

  @Override
  public void run() throws ApplicationException
  {
    execute(alterColumn("mitgliednextbgruppe", new Column("mitglied",
        COLTYPE.BIGINT, 19, null, false, false)));
    execute(alterColumn("mitgliednextbgruppe", new Column("beitragsgruppe",
        COLTYPE.BIGINT, 19, null, false, false)));

    Index idx = new Index("ixMitgliednextbgruppe2", false);
    Column col = new Column("beitragsgruppe", COLTYPE.INTEGER, 0, null, false,
        false);
    idx.add(col);
    execute(idx.getCreateIndex("mitgliednextbgruppe"));

    // Liquibase id=116
    execute(createForeignKey("fkMitgliednextbgruppe1", "mitgliednextbgruppe",
        "beitragsgruppe", "beitragsgruppe", "id", "RESTRICT", "NO ACTION"));
  }
}
