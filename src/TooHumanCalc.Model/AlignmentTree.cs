namespace TooHumanCalc.Model
{
	public class AlignmentTree
	{
		public string Name { get; set; }
		public string Description { get; set; }
		public string ImagePath { get; set; }

		public SkillNode FirstNode { get; set; }
		public SkillNode SecondNode { get; set; }
		public SkillPath LeftPath { get; set; }
		public SkillPath RightPath { get; set; }
		public SkillNode LastNode { get; set; }
	}
}