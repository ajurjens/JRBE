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

import java.util.ArrayList;

/**
 *
 * @author Alexander Jurjens
 */
public class Region {
    
    private String name;
    private double cumProd;
    private int cumYear;
    private ArrayList <Double> readProd = new ArrayList<>();
    private ArrayList <Double> readDem = new ArrayList<>();
    private double demandPercentage;
    private ArrayList <Integer> readTimeline = new ArrayList<>();
    private int firstYear;
    private int lastYear;
    private ArrayList <Double> pDivQ = new ArrayList<>();
    private ArrayList <Double> Q = new ArrayList<>();
    private int multiMaximumDataPoints[]; // size 4
    private int multiAdditionDataPoints[]; // size 4
    private ArrayList<Integer> demandTimeline = new ArrayList<>();
    
    public Region() {}
    
    public void Region(String name) {
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setCumProd(double cumProd) {
        this.cumProd = cumProd;
    }
    
    public double getCumProd() {
        return cumProd;
    }
    
    public void setCumYear(int cumYear) {
        this.cumYear = cumYear;
    }
    
    public int getCumYear() {
        return cumYear;
    }
    
    public void setReadProd(ArrayList <Double> readProd) {
        this.readProd = readProd;
    }
    
    public ArrayList <Double> getReadProd() {
        return readProd;
    }
    
    public void setReadTimeline(ArrayList <Integer> readTimeline) {
        this.readTimeline = readTimeline;
    }
    
    public ArrayList <Integer> getReadTimeline() {
        return readTimeline;
    }
    
    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }
    public int getFirstYear() {
        return firstYear;
    }
    
    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }
    
    public int getLastYear() {
        return lastYear;
    }
    
    public void setPDivQ(ArrayList <Double> pDivQ) {
        this.pDivQ = pDivQ;
    }
    
    public ArrayList <Double> getPDivQ() {
        return pDivQ;
    }
    
    public void setQ(ArrayList <Double> Q) {
        this.Q = Q;
    }
    
    public ArrayList <Double> getQ() {
        return Q;
    }
    
    public void setMultiAdditionDataPoints(int points[]) {
        multiAdditionDataPoints = points;
    }
    
    public void setMultiMaximumDataPoints(int points[]) {
        multiMaximumDataPoints = points;
    }
    
    public int[] getMultiMaximumDataPoints() {
        return multiMaximumDataPoints;
    }
    
    public int[] getMultiAdditionDataPoints() {
        return multiMaximumDataPoints;
    }
    
    public void setDemandPercentage(double percentage) {
        demandPercentage = percentage;
    }
    
    public void setDemand(ArrayList <Double> demand) {
        readDem = demand;
    }
    
    public void setDemandTimeline(ArrayList<Integer> demandTimeline) {
        this.demandTimeline = demandTimeline;
    }
    
    public double getDemandPercentage() {
        return demandPercentage;
    }
    
    public ArrayList<Double> getDemand() {
        return readDem;
    }
    
    public ArrayList<Integer> getDemandTimeline() {
        return demandTimeline;
    }
}