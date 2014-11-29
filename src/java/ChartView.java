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
import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Alexander Jurjens
 */
@ManagedBean
public class ChartView implements Serializable {
 
    private LineChartModel lineModel;
    private Analyzer analyzer;
     
    @PostConstruct
    public void init() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            analyzer = new Analyzer(context);
            analyzer.readData();
        } catch (IOException ex) {
            Logger.getLogger(ChartView.class.getName()).log(Level.SEVERE, null, ex);
            showFatalError("Cannot read data.");
        }
        createLineModels();
    }
 
    public LineChartModel getLineModel1() {
        return lineModel;
    }
     
    private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setTitle("Oil Production");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        
        
 
        /*LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);*/
         
        return model;
    }
    
    public void showFatalError(String error) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal Error!", error + "\nPlease contact Alexander ASAP!");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
