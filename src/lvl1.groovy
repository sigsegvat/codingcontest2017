
class Location {

    String name
    int x,y

    public Location(String name, x,y)
    {
        this.name = name
        this.x = x as Integer
        this.y= y as Integer

    }


    String toString() { this.properties }
}


def lines = new File("levels/1/level1-4.txt").readLines()

int nr =  lines[0] as Integer

println lines[1..nr]

Map<String,Location> locations = new HashMap<>()

lines[1..nr].forEach({ line ->

    def tokenize = line.tokenize()
    def loc =  tokenize as Location
    locations.put(loc.name, loc)

})

def (from,to) = lines[-1].tokenize()

println "$from $to"
println Math.sqrt((locations[from].x -locations[to].x ).power(2) +(locations[from].y -locations[to].y ).power(2)) / 250 + 200

