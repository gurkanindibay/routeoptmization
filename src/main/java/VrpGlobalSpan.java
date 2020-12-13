import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingDimension;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Minimal VRP.
 */
public class VrpGlobalSpan {
    static {
        System.loadLibrary("jniortools");
    }

    private static final Logger logger = Logger.getLogger(VrpGlobalSpan.class.getName());

    private String[] coordinateMatrix = { "3610+Hacks+Cross+Rd+Memphis+TN", "1921+Elvis+Presley+Blvd+Memphis+TN",
            "149+Union+Avenue+Memphis+TN", "1034+Audubon+Drive+Memphis+TN", "1532+Madison+Ave+Memphis+TN",
            "706+Union+Ave+Memphis+TN", "3641+Central+Ave+Memphis+TN", "926+E+McLemore+Ave+Memphis+TN",
            "4339+Park+Ave+Memphis+TN", "600+Goodwyn+St+Memphis+TN", "2000+North+Pkwy+Memphis+TN",
            "262+Danny+Thomas+Pl+Memphis+TN", "125+N+Front+St+Memphis+TN", "5959+Park+Ave+Memphis+TN", "814+Scott+St+Memphis+TN",
            "1005+Tillman+St+Memphis+TN" };

    static class DataModel {
        public  long[][] distanceMatrix = {
                { 0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662 },
                { 548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210 },
                { 776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754 },
                { 696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358 },
                { 582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244 },
                { 274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708 },
                { 502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480 },
                { 194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856 },
                { 308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514 },
                { 194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468 },
                { 536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354 },
                { 502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844 },
                { 388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730 },
                { 354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536 },
                { 468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194 },
                { 776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798 },
                { 662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0 }, };

        public final int vehicleNumber = 10;

        public final int depot = 0;
    }

    int[][] createDataMatrix(){
        String[] addresses = coordinateMatrix;
        return new int[0][0];
    }

    /// @brief Print the solution.
    static void printSolution(DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        // Inspect solution.
        long maxRouteDistance = 0;
        for (int i = 0; i < data.vehicleNumber; ++i) {
            long index = routing.start(i);
            logger.info("Route for Vehicle " + i + ":");
            long routeDistance = 0;
            String route = "";
            while (!routing.isEnd(index)) {
                route += manager.indexToNode(index) + " -> ";
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
            logger.info(route + manager.indexToNode(index));
            logger.info("Distance of the route: " + routeDistance + "m");
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        logger.info("Maximum of the route distances: " + maxRouteDistance + "m");
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get("out.txt"));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) throws Exception {
        // Instantiate the data problem.
        final DataModel data = new DataModel();
        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDWspiiu2xZwfxBCtt4_B9tcdHR-uEZYvY").build();

        long[][] distanceMatrix=null ;
                Gson gson = new Gson();
        String lines =  Files.readString(Paths.get("out.txt"), Charset.defaultCharset());
            System.out.println(lines);
           distanceMatrix = gson.fromJson(lines.toString(),long[][].class);


        data.distanceMatrix = distanceMatrix;

        // Create Routing Index Manager
        RoutingIndexManager manager = new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
            // Convert from routing variable Index to user NodeIndex.
            int fromNode = manager.indexToNode(fromIndex);
            int toNode = manager.indexToNode(toIndex);
            return data.distanceMatrix[fromNode][toNode];
        });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Distance constraint.
        routing.addDimension(transitCallbackIndex, 0, 1000000000, true, // start cumul to zero
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters().toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.GLOBAL_CHEAPEST_ARC).build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        printSolution(data, routing, manager, solution);
    }
}