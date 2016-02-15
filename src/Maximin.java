import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Антон on 08.02.2016.
 */
public class Maximin {
    private  int countOfPoints;
    private List<Point> points;
    private static List<Cluster> clusters;
    private Dimension screenSize;
    private Double maxDistance;
    private Point newCentroid;


    public Maximin() {
        this.points = new ArrayList<Point>();
        this.clusters = new ArrayList<Cluster>();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void setCountOfPoints(int countOfPoints){
        this.countOfPoints = countOfPoints;
    }


    public void createPoints() {
        points = Point.createRandomPoints(countOfPoints, screenSize.getWidth(), screenSize.getHeight());
        Cluster cluster = new Cluster(getRandomColor());
        Point centroid = Point.createRandomPoint(screenSize.getWidth(), screenSize.getHeight());
        centroid.setClusterColor(cluster.getColor());
        cluster.setCentroid(centroid);
        clusters.add(cluster);
    }

    private Color getRandomColor() {

        Random random = new Random();

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        return new Color(r, g, b);
    }

    private  void setSecondCentroid(){
        double min = Double.MIN_VALUE;
        double max = min;
        Point pointToCentroid = null;
        double distance = 0.0;
            Cluster cluster = clusters.get(0);
            Point centroid = cluster.getCentroid();
            for (Point point : points){
                distance = Point.distance(point, centroid);
                if(distance>max){
                    max = distance;
                    pointToCentroid = point;
                }
        }
        Cluster newCluster = new Cluster(getRandomColor());
        newCluster.setCentroid(pointToCentroid);
        clusters.add(newCluster);
    }

    private boolean isSetNewCentroid(){
        calculateMaxDistance();
        double totalDistance = 0;
        List<Double> distances = new ArrayList<Double>();
        for (int i = 0; i < clusters.size()-1; i++) {
            for (int j = 1; j <clusters.size() ; j++) {
                double currentDistance = Point.distance(clusters.get(i).getCentroid(), clusters.get(j).getCentroid());
                distances.add(currentDistance);
                totalDistance+=currentDistance;
            }

        }

        if(maxDistance > totalDistance/distances.size()/2){
            Cluster newCluster = new Cluster(getRandomColor());
            newCluster.setCentroid(newCentroid);
            clusters.add(newCluster);
            return true;
        }
        return false;
    }


    public void calculate() {

        setSecondCentroid();

        while (true) {
            clearClusters();
            assignClusters();
            calculateMaxDistance();
            if(!isSetNewCentroid())
                break;

        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }


    private void calculateMaxDistance(){
        double max = Double.MIN_VALUE;
        Point pointToCentroid = null;
        double distance = 0.0;
        for (Cluster cluster : clusters){
            Point centroid = cluster.getCentroid();
            List<Point> points = cluster.getPoints();
            for (Point point : points){
                distance = Point.distance(point, centroid);
                if(distance>max){
                    max = distance;
                    pointToCentroid = point;
                }
            }
        }
        maxDistance = max;
        newCentroid = pointToCentroid;
    }

    private void assignClusters() {
        double max = Double.MAX_VALUE;
        double min = max;
        Color clusterColor = null;
        double distance = 0.0;
        int cluster = 0;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < clusters.size(); i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    clusterColor = c.getColor();
                    cluster = i;
                }
            }

            point.setClusterColor(clusterColor);
            clusters.get(cluster).addPoint(point);
        }
    }




    public List<Cluster> getClusters(){
        return clusters;
    }

    public void removeAllClusters(){
        clusters.clear();
    }
}

