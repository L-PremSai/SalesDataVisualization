package Visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesVisualizationApp extends JFrame {

    private JTextField[] salesInputFields;
    private JButton visualizeButton;
    private JPanel chartPanel;

    public SalesVisualizationApp() {
        setTitle("Sales Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Increased size to accommodate more inputs and charts

        // Create components
        salesInputFields = new JTextField[10];
        visualizeButton = new JButton("Visualize");
        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizeSales();
            }
        });

        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 2)); // Two charts side by side

        JPanel inputPanel = new JPanel(new GridLayout(11, 2)); // 11 rows for 10 inputs + 1 button
        for (int i = 0; i < 10; i++) {
            JLabel label = new JLabel("Week " + (i + 1) + ":");
            salesInputFields[i] = new JTextField(10);
            inputPanel.add(label);
            inputPanel.add(salesInputFields[i]);
        }
        inputPanel.add(new JLabel("")); // Empty label for layout
        inputPanel.add(visualizeButton);

        // Add components to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(chartPanel, BorderLayout.CENTER);
    }

    private void visualizeSales() {
        // Read input values
        double[] salesData = new double[10];
        for (int i = 0; i < 10; i++) {
            String input = salesInputFields[i].getText().trim();
            try {
                salesData[i] = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for Sales " + (i + 1) + ": " + input, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Create pie chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 0; i < 10; i++) {
            pieDataset.setValue("Category " + (i + 1), salesData[i]);
        }
        JFreeChart pieChart = ChartFactory.createPieChart("Sales Distribution", pieDataset, true, true, false);

        // Create bar chart
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (int i = 0; i < 10; i++) {
            barDataset.addValue(salesData[i], "Sales", "Category " + (i + 1));
        }
        JFreeChart barChart = ChartFactory.createBarChart("Sales by Category", "Category", "Sales", barDataset, PlotOrientation.VERTICAL, true, true, false);

        // Display charts in the chartPanel
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(pieChart));
        chartPanel.add(new ChartPanel(barChart));
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SalesVisualizationApp app = new SalesVisualizationApp();
                app.setVisible(true);
            }
        });
    }
}
