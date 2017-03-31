
class HyperMap {
    Map<String,Location> locations = new HashMap<>()

    String hypStart, hypEnd

    def closest(loc) {
       if( distance(loc,hypStart) < distance(loc,hypEnd)) {
           return hypStart
       } else {
           return hypEnd
       }
    }

    def distance(String from, to){
        Math.sqrt((locations[from].x -locations[to].x ).power(2) +(locations[from].y -locations[to].y ).power(2))
    }

    def drivingTime(String from, to) {
        distance(from,to) /15
    }

    def hyperLoopTime(String from,to) {
        distance(from,to) / 250 + 200
    }


}

def lines = new File("levels/2/level2-4.txt").readLines()

int nr =  lines[0] as Integer

println lines[1..nr]

def hypermap = new HyperMap()


lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc =  tokenize as Location
    hypermap.locations.put(loc.name, loc)

})

def (journeyStart,journeyEnd) = lines[-2].tokenize()
def (hypStart,hypEnd) = lines[-1].tokenize()
hypermap.hypStart = hypStart
hypermap.hypEnd = hypEnd

println "$journeyStart $journeyEnd"
println "$hypStart $hypEnd"


String startClosest = hypermap.closest(journeyStart)
def part1 = hypermap.drivingTime(journeyStart,startClosest)
String notClostest = hypStart.equals( startClosest) ? hypEnd : hypStart
def part2 = hypermap.hyperLoopTime(startClosest, notClostest)
def part3 = hypermap.drivingTime(notClostest,journeyEnd)
println "$hypStart $startClosest $notClostest"
println "$part1 $part2 $part3"
println (part1 + part2 + part3)
println Math.round(part1 + part2 + part3)

