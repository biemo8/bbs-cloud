let password = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
  "p","q","r","s","t","u","v","w","x","y","z","A","B","C","D",
  "E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
  "T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"
]

export function ramdomStrKey (length) {
  let Str = ""
  for(let i = 0; i < length; i++){
    Str += password[Math.floor(Math.random()*password.length)]
  }
  return Str
}
