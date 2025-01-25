package org.mql.java.ui;

import org.mql.java.enumeration.RelationType;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.UMLRelation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagramGen extends JPanel {

    private static final long serialVersionUID = 1L;
    private List<ClassEntity> classes;
    private Map<String, Point> classLocations;

    public DiagramGen(String name, List<ClassEntity> classes) {
        this.classes = classes;
        this.classLocations = new HashMap<>();
        setPreferredSize(new Dimension(1000, 1000));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
//    	
//    	for (ClassEntity cls : classes) {
//    	    System.out.println("Class: " + cls.getName());
//    	    for (UMLRelation rel : cls.getRelations()) {
//    	        System.out.println("  Relation: " + rel.getType() + " to " + rel.getTo());
//    	    }
//    	}

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 3;

        int numClasses = classes.size();
        double angleStep = 2 * Math.PI / numClasses;

        for (int i = 0; i < numClasses; i++) {
            double angle = i * angleStep;
            int x = (int) (centerX + radius * Math.cos(angle)) - 100;
            int y = (int) (centerY + radius * Math.sin(angle)) - 50;

            Point location = new Point(x, y);
            classLocations.put(classes.get(i).getName(), location);
            drawClass(g2, classes.get(i), location);
        }

        drawRelationships(g2);
    }

    private void drawClass(Graphics2D g2, ClassEntity cls, Point location) {
        int width = 150;
        int x = location.x, y = location.y;

        // Class name header
        int height = 30;
        g2.setColor(new Color(70, 130, 180));
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString(cls.getName(), x + 10, y + 20);

        // Fields and Methods sections
        int sectionStartY = y + height;
        int sectionHeight = drawSection(g2, cls.getFields(), x, sectionStartY, width);
        sectionStartY += sectionHeight;
        drawSection(g2, cls.getMethods(), x, sectionStartY, width);

        // Draw class rectangle
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, sectionStartY - y);
    }

    private int drawSection(Graphics2D g2, List<String> items, int x, int y, int width) {
        int sectionHeight = items.size() * 15;
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, sectionHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, sectionHeight);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        int currentY = y + 12;
        for (String item : items) {
            g2.drawString(item, x + 5, currentY);
            currentY += 15;
        }
        return sectionHeight;
    }

    private void drawRelationships(Graphics2D g2) {
        for (ClassEntity cls : classes) {
            for (UMLRelation rel : cls.getRelations()) {
                Point from = classLocations.get(cls.getName());
                Point to = classLocations.get(rel.getTo()); // Assurez-vous que rel.getTo() renvoie un nom valide.
                if (from != null && to != null) {
                    // Passez le type de RelationType directement.
                    drawRelationLine(g2, from, to, rel.getType());
                    
                }
            }
        }
    }

    private void drawRelationLine(Graphics2D g2, Point from, Point to, RelationType relationType) {
        int width = 150;
        int height = 80;

        // Calculez les points de connexion.
        Point start = calculateConnectionPoint(from, to, width, height);
        Point end = calculateConnectionPoint(to, from, width, height);

        switch (relationType) {
            case INHERITANCE:
                drawArrow(g2, start, end); 
                break;
            case AGGREGATION:
                drawDiamond(g2, start, end, false); 
                break;
            case COMPOSITION:
                drawDiamond(g2, start, end, true); 
                break;
            case IMPLEMENTATION:
                drawDashedLine(g2, start, end); 
                drawHollowArrow(g2, start, end); 
                break;
            default:
                g2.setColor(Color.BLACK);
                g2.drawLine(start.x, start.y, end.x, end.y);
                break;
        }
    }


    private Point calculateConnectionPoint(Point source, Point target, int width, int height) {
        int x = source.x + width / 2;
        int y = source.y + height / 2;

        if (source.y + height < target.y) {
            y = source.y + height;
        } else if (source.y > target.y + height) {
            y = source.y;
        }

        if (source.x + width < target.x) {
            x = source.x + width;
        } else if (source.x > target.x + width) {
            x = source.x;
        }

        return new Point(x, y);
    }

    private void drawDiamond(Graphics2D g2, Point from, Point to, boolean filled) {
        int diamondSize = 10;

        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double length = Math.sqrt(dx * dx + dy * dy);

        double unitDx = dx / length;
        double unitDy = dy / length;

        int offsetX = (int) (unitDx * diamondSize);
        int offsetY = (int) (unitDy * diamondSize);

        int centerX = from.x + offsetX;
        int centerY = from.y + offsetY;

        int[] xPoints = {centerX, centerX - diamondSize, centerX, centerX + diamondSize};
        int[] yPoints = {centerY - diamondSize, centerY, centerY + diamondSize, centerY};

        Polygon diamond = new Polygon(xPoints, yPoints, 4);

        if (filled) {
            g2.setColor(Color.BLACK);
            g2.fillPolygon(diamond);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillPolygon(diamond);
        }

        g2.setColor(Color.BLACK);
        g2.drawPolygon(diamond);
    }

    private void drawDashedLine(Graphics2D g2, Point start, Point end) {
        float[] dashPattern = {10.0f, 10.0f};
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
        g2.drawLine(start.x, start.y, end.x, end.y);
        g2.setStroke(originalStroke);
    }

    private void drawArrow(Graphics2D g2, Point start, Point end) {
        int arrowSize = 20;
        double angle = Math.atan2(end.y - start.y, end.x - start.x);

        int x1 = end.x - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = end.y - (int) (arrowSize * Math.sin(angle - Math.PI / 6));

        int x2 = end.x - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = end.y - (int) (arrowSize * Math.sin(angle + Math.PI / 6));

        Polygon arrowHead = new Polygon(new int[]{end.x, x1, x2}, new int[]{end.y, y1, y2}, 3);
        g2.fillPolygon(arrowHead);
    }

    private void drawHollowArrow(Graphics2D g2, Point start, Point end) {
        int arrowSize = 10;
        double angle = Math.atan2(end.y - start.y, end.x - start.x);

        int x1 = end.x - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = end.y - (int) (arrowSize * Math.sin(angle - Math.PI / 6));

        int x2 = end.x - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = end.y - (int) (arrowSize * Math.sin(angle + Math.PI / 6));

        g2.setColor(Color.WHITE);
        Polygon hollowArrow = new Polygon(new int[]{end.x, x1, x2}, new int[]{end.y, y1, y2}, 3);
        g2.fillPolygon(hollowArrow);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(hollowArrow);
    }
}
