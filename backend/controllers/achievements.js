const Achievement=require('../models/achievements')
const {updateBadge}=require('./badge')

async function addUser(req,res){
    const {alias}=req.params
    await Achievement.create({alias})
    return res.status(201).json({message:'User added'})
}

async function getAchievements(req,res){
    const {alias}=req.params
    const achievement=await Achievement.findOne({alias})
    if(!achievement){
        return res.status(404).json({error:'Alias not found'})
    }
    return res.status(200).json({achievement})
}

async function updateAchievements(req,res){
    const {alias}=req.params
    const {helped}=req.body
    console.log(helped)
    const achievement=await Achievement.findOne({alias})
    if(!achievement){
        return res.status(404).json({error:'Alias not found'})
    }
    if(helped){
        achievement.completed+=1
        achievement.streak+=1
        achievement.streakRecord=Math.max(achievement.streakRecord,achievement.streak)
        achievement.lastHelped=Date.now()
    }
    else{
        achievement.streak=0
    }
    
    updateBadge(achievement)

    await achievement.save()
    return res.status(200).json({message:'Streak updated'})
}

module.exports={getAchievements,updateAchievements,addUser}