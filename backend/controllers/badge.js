 function updateBadge(achievement){
    if(achievement.completed===1){
        achievement.achievements=['First Step']
    }
    else if(achievement.completed===15){
        achievement.achievements=['First Step','Heart of Silver']
    }
    else if(achievement.completed===25){
        achievement.achievements=['First Step','Heart of Silver','Heart of Gold']
    }
    else if(achievement.completed===50){
        achievement.achievements=['First Step','Heart of Silver','Heart of Gold','Salvatore Lite']
    }
    else if(achievement.completed===75){
        achievement.achievements=['First Step','Heart of Silver','Heart of Gold','Salvatore Lite','Salvatore']
    }
    else if(achievement.completed===100){
        achievement.achievements=['First Step','Heart of Silver','Heart of Gold','Salvatore Lite','Salvatore','Salvatore Pro']
    }

    if(achievement.streakRecord===3){
        achievement.achievements.push('Streak Rookie')
    }
    else if(achievement.streakRecord===5){
        achievement.achievements.push('Streakster')
    }
    else if(achievement.streakRecord===10){
        achievement.achievements.push('Streak Master')
    }
    
 }

 module.exports={updateBadge}