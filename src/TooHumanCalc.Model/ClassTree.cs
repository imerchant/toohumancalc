namespace TooHumanCalc.Model
{
	public class ClassTree
	{
		public string Name { get; set; }
		public string Description { get; set; }
		public Specs Specs { get; set; }

		public SkillNode RuinerNode { get; set; }
		public SkillNode GroupNode { get; set; }
		public SkillPath LeftPath { get; set; }
		public SkillPath CenterPath { get; set; }
		public SkillPath RightPath { get; set; }
		public SkillNode FenrirNode { get; set; }
	}
}
