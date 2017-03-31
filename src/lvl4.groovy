def lines = new File("levels/4/level4-4.txt").readLines()

int nr = lines[0] as Integer

def hypermap = new HyperMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc = tokenize as Location
    hypermap.locations.put(loc.name, loc)

})
def nrOfJourneys = lines[nr + 1] as Integer

def journeys = []

lines[nr + 2..-2].forEach({ line ->
    def journey = line.tokenize() as Journey
    journeys << journey

})

def travelTime(Journey jour, HyperMap hypermap) {
    String startClosest = hypermap.closest(jour.from)
    def part1 = hypermap.drivingTime(jour.from, startClosest)
    String notClostest = hypermap.hypStart.equals(startClosest) ? hypermap.hypEnd : hypermap.hypStart

    def part2 = hypermap.hyperLoopTime(startClosest, notClostest)

    def part3 = hypermap.drivingTime(notClostest, jour.to)


    def hyperTrip = Math.round(part1 + part2 + part3)

    return hyperTrip < jour.travelTime
}


def nrOfBetters = lines[-1] as Integer
int faster = 0





for ( l1 in hypermap.locations) {
    for ( l2 in hypermap.locations) {
        if (l1.value.name == l2.value.name) {
            continue;
        }
        hypermap.hypStart = l1.value.name
        hypermap.hypEnd = l2.value.name





        journeys.forEach({ j ->
            if (travelTime(j, hypermap)) {
                faster++
            }
        })
        if(faster>=nrOfBetters) {
            println "found($faster $nrOfBetters) $hypermap.hypStart $hypermap.hypEnd"
        }else {
            println "tried $hypermap.hypStart $hypermap.hypEnd"
        }
        faster = 0
    }
}



