def lines = new File("levels/6/level6-4.txt").readLines()

int nr = lines[0] as Integer

println lines[1..nr]

def hypermap = new HyperMultiMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc = tokenize as Location
    hypermap.locations.put(loc.name, loc)

})

def nrOfJourneys = lines[nr + 1] as Integer

def journeys = []
Map<String, Integer> journeyCount = new HashMap()

lines[nr + 2..-3].forEach({ line ->
    def journey = line.tokenize() as Journey
    journeys << journey

    def fromcnt = journeyCount.get(journey.from)
    fromcnt = fromcnt == null ? 1 : fromcnt + 1
    journeyCount.put(journey.from, fromcnt)

    def tocnt = journeyCount.get(journey.from)
    tocnt = tocnt == null ? 1 : tocnt + 1
    journeyCount.put(journey.to, tocnt)

})

journeyCount = journeyCount.sort { b, a -> a.value <=> b.value }

def nrOfBetters = lines[-2] as Integer
def maxNumberMeter = lines[-1] as Integer

println "JOUR $journeys"
println "JOUR $journeyCount"
println "HPY $hypermap.locations"
println "$nrOfBetters"
println "$maxNumberMeter"

def calcHyperMap(Journey j, HyperMultiMap hypermap) {
    String startClosest = hypermap.closest(j.from)
    String endClosest = hypermap.closest(j.to)

    def part1 = hypermap.drivingTime(j.from, startClosest)
    def part2 = hypermap.hyperLoopTime(startClosest, endClosest)
    def part3 = hypermap.drivingTime(endClosest, j.to)

    //println "$part1 $part2 $part3"
    return Math.round(part1 + part2 + part3)
}


def check(List<Journey> journeys, HyperMultiMap hypermap, nrOfBetters, maxNumberMeter) {

    def meters = hypermap.calcMeters()
    if (meters > maxNumberMeter) {
        //println "tried meters $hypermap.hyperroute $maxNumberMeter $meters"
        return
    }

    faster = 0
    journeys.forEach({ j ->
        if (calcHyperMap(j, hypermap) < j.travelTime) {
            faster++
        }
    })
    if (faster >= nrOfBetters) {
        def r = hypermap.hyperroute.join(" ")
        println "found($faster $nrOfBetters) $hypermap.hyperroute.size $r"
    } else {
        //println "tried $hypermap.hyperroute"
    }


}

def N = 4
for (N = 2; N < (nr / 4); N++) {
    println(N)
    Math.min(N,1500).times { i ->
        if(i%25==0)print("+")
        def hotspots = journeyCount.keySet().toArray()[0..2 * N]
        Collections.shuffle(hotspots)
        hotspots = hotspots[0..N - 1]
        hypermap.hyperroute = hotspots
        Collections.shuffle(hypermap.hyperroute)
        check(journeys, hypermap, nrOfBetters, maxNumberMeter)

    }
    println("!")
}