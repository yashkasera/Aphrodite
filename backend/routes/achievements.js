const router = require('express').Router();
const { getAchievements, updateAchievements , addUser} = require('../controllers/achievements');
const Achievement=require('../models/achievements')

router.route('/:alias')
.get(getAchievements)
.post(addUser)
.patch(updateAchievements)
.delete(async (req,res)=>{
    await Achievement.deleteOne({alias:req.params.alias})
    return res.status(200).json({message:'User deleted'})
});

module.exports = router;