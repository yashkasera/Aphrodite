const mongoose=require('mongoose')

const AchievementSchema=new mongoose.Schema({
    alias:{
        type:String,
        required:true,
        unique:true
    },
    completed:{
        type:Number,
        required:true,
        default:0
    },
    achievements:{
        type:[String],
        enum:['First Step','Heart of Silver','Heart of Gold','Streak Rookie','Streakster','Streak Master','Salvatore Lite','Salvatore','Salvatore Pro'],
        required:true,
        default:[]
    },
    streak:{
        type:Number,
        required:true,
        default:0
    },
    streakRecord:{
        type:Number,
        required:true,
        default:0
    },
    lastHelped:{
        type:Date,
    },
})

const Achievement=mongoose.model('Achievement',AchievementSchema)

module.exports=Achievement