class HyperHyperMultiMap {
    Map<String, Location> locations = new HashMap<>()

    List<List<String>> hyperroute = new ArrayList<>()

    String hub;

    def closest(loc) {
        long best = Long.MAX_VALUE
        def bestLoc

        for (line in hyperroute) {
            for (l in line) {
                def curr = distance(loc, l)
                if (curr < best) {
                    best = curr
                    bestLoc = l
                };
            }
        }
        return bestLoc

    }

    def distance(String from, to) {
        Math.sqrt((locations[from].x - locations[to].x).power(2) + (locations[from].y - locations[to].y).power(2))
    }

    def drivingTime(String from, to) {
        distance(from, to) / 15
    }

    def calcMeters() {
        long meters = 0
        for (int i = 0; (i < hyperroute.size() - 1); i++) {
            meters += distance(hyperroute[i], hyperroute[i + 1])
        }
        return meters
    }

    def getLine(station) {
        for (line in hyperroute) {
            for (l in line) {
                if (l == station) return line
            }
        }
    }

    def hyperLoopTime(String from, to) {
        if (getLine(from) == getLine(to)) return hyperLoopTimeSameLine(from, to, getLine(from))


        def line = hyperLoopTimeSameLine(from, hub, getLine(from))

        def line1 = hyperLoopTimeSameLine(hub, to, getLine(to))

        //FAIL....time over :-(((
        return line + 300 + line1
    }

    def hyperLoopTimeSameLine(String from, to, line) {
        def last = line[0]
        def time = 0
        def inTrain = false
        if(from==to )return 0;
        for (cur in line) {
            if (from == cur) {
                inTrain = true

            } else if (inTrain) {
                time += 200
                time += distance(last, cur) / 250
            }



            if (cur == to && inTrain) {

                inTrain = false
                break;
            }

            last = cur
        }
        if (inTrain) return hyperLoopTime(to, from)

        return time
    }


}

def lines = new File("levels/7/level7-eg.txt").readLines()

int nr = lines[0] as Integer

println lines[1..nr]

def hypermap = new HyperHyperMultiMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc = tokenize as Location
    hypermap.locations.put(loc.name, loc)

})

def (journeyStart, journeyEnd) = lines[nr + 1].tokenize()
hypermap.hub = lines[nr + 2]
def nrofLines = lines[nr + 3]

lines[nr + 4..-1].forEach({ line ->
    hypermap.hyperroute << line.tokenize()[1..-1]
})
println "$journeyStart $journeyEnd"
println "$hypermap.hyperroute"


String startClosest = hypermap.closest(journeyStart)
String endClosest = hypermap.closest(journeyEnd)

def part1 = hypermap.drivingTime(journeyStart, startClosest)
def part2 = hypermap.hyperLoopTime(startClosest, endClosest)
def part3 = hypermap.drivingTime(endClosest, journeyEnd)

println "$part1 $part2 $part3"
println Math.round(part1 + part2 + part3)

