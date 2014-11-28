
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;
import javax.servlet.ServletContext;

/*
 * Copyright (C) 2014 Alexander
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/**
 *
 * @author Alexander
 */
public class Analyzer {
    
    private final ServletContext context;
    private int cumYear;
    private double cumProd;
    private int first;
    private int last;
    
    public Analyzer(ServletContext context) throws IOException {
        this.context = context;
        readData();
    }
    
    private void readData() throws IOException {
        
        int count = 0;
        
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(context.getRealPath("/Data")))) {
            for (Path path : dirStream) {
                if (path.toString().endsWith(".txt")) {
                    try (Stream<String> lines = Files.lines(path)) {
                        Iterator<String> it = lines.iterator();
                        while(it.hasNext()) {
                            String line = it.next();
                            char c = line.charAt(0);
                            boolean isDigit = (c >= '0' && c <= '9');
                            if (isDigit) {
                                if (count == 0) {
                                    count++;
                                    String [] data = line.split(" ");
                                    cumYear = Integer.parseInt(data[0]);
                                    cumProd = Double.parseDouble(data[1]);
                                }
                                if (count == 1) {
                                    count++;
                                    String [] data = line.split(" ");
                                    first = Integer.parseInt(data[0]);
                                    last = Integer.parseInt(data[1]);
                                }
                                if (count == 2 || count == 3) {
                                    count++;
                                    String [] data = line.split(" ");
                                    first = Integer.parseInt(data[0]);
                                    last = Integer.parseInt(data[1]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
