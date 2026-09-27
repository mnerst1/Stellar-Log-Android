package com.stellarlog.app.domain
data class CelestialTarget(val id: String, val name: String, val detail: String, val type: String, val constellation: String, val magnitude: String, val description: String, val icon: String)
object TargetCatalog {
 val targets = listOf(
  CelestialTarget("m31","Andromeda Galaxy","M31 · Spiral galaxy","Galaxy","Andromeda","3.4","Our nearest large galactic neighbour, spanning six full moons across the sky.","🌀"),
  CelestialTarget("m42","Orion Nebula","M42 · Emission nebula","Nebula","Orion","4.0","A stellar nursery visible to the naked eye beneath Orion’s belt.","☁️"),
  CelestialTarget("m45","Pleiades","M45 · Open cluster","Cluster","Taurus","1.6","The Seven Sisters: a bright young cluster wrapped in blue reflection nebulosity.","✨"),
  CelestialTarget("m13","Hercules Cluster","M13 · Globular cluster","Cluster","Hercules","5.8","A dense sphere of hundreds of thousands of ancient stars.","🌌"),
  CelestialTarget("m51","Whirlpool Galaxy","M51 · Interacting galaxies","Galaxy","Canes Venatici","8.4","A face-on spiral galaxy interacting with its smaller companion NGC 5195.","🌀"),
  CelestialTarget("m57","Ring Nebula","M57 · Planetary nebula","Nebula","Lyra","8.8","The glowing shell of a dying star, appearing as a tiny smoke ring.","⭕"),
  CelestialTarget("vega","Vega","α Lyrae · Blue-white star","Star","Lyra","0.0","One of the brightest stars in the northern sky and a vertex of the Summer Triangle.","⭐"),
  CelestialTarget("jupiter","Jupiter","Planet · Gas giant","Planet","Solar System","−2.7","Even small binoculars can reveal Jupiter’s four largest moons.","🪐"),
  CelestialTarget("m27","Dumbbell Nebula","M27 · Planetary nebula","Nebula","Vulpecula","7.5","A bright planetary nebula with a distinctive apple-core shape.","☁️"),
  CelestialTarget("albireo","Albireo","β Cygni · Double star","Star","Cygnus","3.1","A striking gold and sapphire pair, beautifully split in a small telescope.","💫")
 )
}