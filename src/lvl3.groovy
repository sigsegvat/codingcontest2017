
class Journey {
    String from, to
    int travelTime

    Journey(String from, String to, String travelTime) {
        this.from = from
        this.to  = to
        this.travelTime = travelTime as Integer
    }

    def String toString() {
        this.properties
    }
}


def lines = new File("levels/3/level3-4.txt").readLines()

int nr =  lines[0] as Integer

def hypermap = new HyperMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc =  tokenize as Location
    hypermap.locations.put(loc.name, loc)

})
def nrOfJourneys  = lines[nr+1] as Integer

def journeys = []

lines[nr+2..-2].forEach({ line ->
    def journey = line.tokenize() as Journey
    journeys << journey

})

println journeys

def (hypStart,hypEnd) = lines[-1].tokenize()
hypermap.hypStart = hypStart
hypermap.hypEnd = hypEnd


println "$hypStart $hypEnd"

int faster = 0
journeys.forEach({j ->
    if(travelTime(j,hypermap)) {
        faster++
    }
})

println faster

def travelTime(Journey jour, HyperMap hypermap) {
    String startClosest = hypermap.closest(jour.from)
    def part1 = hypermap.drivingTime(jour.from, startClosest)
    String notClostest = hypermap.hypStart.equals(startClosest) ? hypermap.hypEnd : hypermap.hypStart

    def part2 = hypermap.hyperLoopTime(startClosest, notClostest)

    def part3 = hypermap.drivingTime(notClostest, jour.to)
    //if(part3 <= 0) println "XXXXXXX $part1 $jour $startClosest $notClostest"

    def hyperTrip = Math.round(part1 + part2 + part3)
    println "$hyperTrip $jour.travelTime "
    //println hyperTrip - jour.travelTime
    return hyperTrip < jour.travelTime
}

println journeys.size()
println hypermap.locations.size()