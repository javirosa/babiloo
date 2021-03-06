/*
 * JaLingo, http://jalingo.sourceforge.net/
 *
 * Copyright (c) 2002-2006 Oleksandr Shyshko
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ja.lingo.engine.reader.util;

import ja.centre.gui.concurrent.StopWatchTemplate;
import ja.centre.util.assertions.Arguments;
import ja.centre.util.io.ByteArray;
import ja.centre.util.io.Files;
import ja.centre.util.io.linereader.ILineReader;
import ja.centre.util.io.linereader.LineReader;
import ja.lingo.application.util.progress.IMonitor;

import java.io.IOException;

class ObservableLineReader extends StopWatchTemplate implements ILineReader {
    private ILineReader lineReader;
    private IMonitor monitor;

    public ObservableLineReader( String fileName, IMonitor monitor ) throws IOException {
        Arguments.assertNotNull( "monitor", monitor );

        this.lineReader = new LineReader( fileName );
        this.monitor = monitor;

        this.monitor.start( 0, (int) Files.length( fileName ) );
    }

    public int getLastLineEnd() {
        return lineReader.getLastLineEnd();
    }

    public ByteArray readLine() throws IOException {
        if ( needsUpdate() ) {
            monitor.update( getLastLineEnd() );
        }

        return lineReader.readLine();
    }

    public void close() throws IOException {
        monitor.finish();
        lineReader.close();
    }

    public int getNextLineStart() {
        return lineReader.getNextLineStart();
    }
}
