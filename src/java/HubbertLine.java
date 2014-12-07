/*
 * Copyright (C) 2014 Alexander Jurjens
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
 * @author Alexander Jurjens
 */
public class HubbertLine {
    
        /*!
        *   The slope of the line
        */
        double slope;

        /*!
        *   The startpoint of the line
        */
        double start; // percentage at x = 0 i.e. crossing with y-axis

        /*!
        *   The qt of the line
        */
        double qt; // Qt i.e URR i.e. y = 0

        /*!
        *   Default constructor.
        */
        public HubbertLine() {};

        /*!
        *   This constructor sets the given values.
        *   \param begin startpoint of the line on the y-axis.
        *   \param slope of the line.
        *   \param qt the x-intercept value of the line
        */
        public HubbertLine(float start, float slope, float qt) {
            this.start = start;
            this.slope = slope;
            this.qt = qt;
        }

        /*!
        *   This function returns the slope of the line.
        *   \return slope
        */
        public double getSlope() { return slope; }

        /*!
        *   This function returns the startpoint of the line.
        *   \return start
        */
        public double getStart() { return start; }

        /*!
        *   This function returns the qt of the line.
        *   \return qt
        */
        public double getQt() { return qt; }

        /*!
        *   This function can be used to set the slope.
        *   \param slope the slope of the line
        */
        public void setSlope(double slope) { this.slope = slope; }

        /*!
        *   This function can be used to set the startpoint
        *   \param start the startpoint of the line
        */
        public void setStart(double start) { this.start = start; }

        /*!
        *   This function can be used to set the qt
        *   \param qt the qt of the line
        */
        public void setQt(double qt) { this.qt = qt; }
}
