namespace TooHumanCalc.Model
{
	public class SkillNode
	{
		public string Name { get; set; }
		public string Description { get; set; }
		public string LevelBonus { get; set; }
		public SkillType Type { get; set; }
		public string ImagePath { get; set; }
		public int Points { get; set; }
		public double Multiplier { get; set; }
		public int Max { get; set; }
	}
}