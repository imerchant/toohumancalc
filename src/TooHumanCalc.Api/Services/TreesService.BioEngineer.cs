using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<ClassTree> _bioEngineerTree = new Lazy<ClassTree>(GetBioEngineerTree);
		public static ClassTree BioEngineerTree => _bioEngineerTree.Value;

		private static ClassTree GetBioEngineerTree()
		{
			return new ClassTree
			{
				Name = "BioEngineer",
				Description = "A master of cybernetics as well as mundane combat, the BioEngineer repairs damage sustained on the battlefield, increasing health bonuses of himself and his allies, enabling them to take the fight directly into the heart of the enemy.",
				ImagePath = string.Empty,
				Specs = new Specs
				{
					HitPoints = 5,
					Melee = 2,
					Ballistics = 2,
					Armor = 2
				},
				RuinerNode = null,
				GroupNode = null,
				LeftPath = new SkillPath
				{
					Nodes = new List<SkillNode>()
				},
				CenterPath = new SkillPath
				{
					Nodes = new List<SkillNode>()
				},
				RightPath = new SkillPath
				{
					Nodes = new List<SkillNode>()
				},
				FenrirNode = null,
				Notes = string.Empty
			};
		}
	}
}
