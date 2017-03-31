class HyperMultiMap {
    Map<String, Location> locations = new HashMap<>()

    List<String> hyperroute

    def closest(loc) {
        long best = Long.MAX_VALUE
        def bestLoc
        for (l in hyperroute) {
            def curr = distance(loc, l)
            if (curr < best) {
                best = curr
                bestLoc = l
            };
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
        for(int i = 0; (i< hyperroute.size()-1); i++) {
            meters += distance(hyperroute[i],hyperroute[i+1])
        }
        return meters
    }

    def hyperLoopTime(String from, to) {
        def last = hyperroute[0]
        def time = 0
        def inTrain = false
        for (cur in hyperroute) {
            if (from == cur) {
                inTrain = true

            } else if (inTrain) {
                time += 200
                time += distance(last, cur) / 250
            }

            if(cur == to && inTrain) {
                inTrain = false
                break;
            }

            last = cur
        }
        if(inTrain) return hyperLoopTime(to, from)

        return time
    }


}

def lines = new File("levels/5/level5-4.txt").readLines()

int nr = lines[0] as Integer

println lines[1..nr]

def hypermap = new HyperMultiMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc = tokenize as Location
    hypermap.locations.put(loc.name, loc)

})

def (journeyStart, journeyEnd) = lines[-2].tokenize()
hypermap.hyperroute = lines[-1].tokenize()[1..-1]


println "$journeyStart $journeyEnd"
println "$hypermap.hyperroute"


String startClosest = hypermap.closest(journeyStart)
String endClosest = hypermap.closest(journeyEnd)

def part1 = hypermap.drivingTime(journeyStart, startClosest)
def part2 = hypermap.hyperLoopTime(startClosest, endClosest)
def part3 = hypermap.drivingTime(endClosest, journeyEnd)

println "$part1 $part2 $part3"
println Math.round(part1 + part2 + part3)

