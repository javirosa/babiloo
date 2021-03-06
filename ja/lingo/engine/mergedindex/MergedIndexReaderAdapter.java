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

package ja.lingo.engine.mergedindex;

import ja.centre.util.assertions.Arguments;
import ja.centre.util.assertions.States;
import ja.centre.util.sort.external.IReader;
import ja.lingo.engine.dictionaryindex.reader.IDictionaryIndex;
import ja.lingo.engine.util.comparators.CollatingStringComparator;

import java.io.IOException;

class MergedIndexReaderAdapter implements IReader<BuildArticle> {
    private IDictionaryIndex reader;
    private int pointer;
    private CollatingStringComparator csc;

    public MergedIndexReaderAdapter( IDictionaryIndex reader, CollatingStringComparator csc ) {
        Arguments.assertNotNull( "reader", reader );
        Arguments.assertNotNull( "csc", csc );

        this.reader = reader;
        this.csc = csc;
    }

    public boolean hasNext() throws IOException {
        return pointer < reader.size();
    }

    public BuildArticle next() throws IOException {
        States.assertTrue( hasNext(), "There is no next element" );

        return new BuildArticle( reader, pointer++, csc );
    }

    public void close() throws IOException {
        // do not close the reader.
        // it will be used further by Engine
    }
}
