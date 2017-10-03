using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<ClassTree> _defenderTree = new Lazy<ClassTree>(GetDefenderTree);
		public static ClassTree DefenderTree => _defenderTree.Value;

		private static ClassTree GetDefenderTree()
		{
			return new ClassTree
			{
				Name = "Defender",
				Description = "With the blessings of ODIN and runes of protection, the Defender is the backbone of the Aesir\'s defense. Heavy armor enables the Defender to absorb a tremendous amount of damage, leaving his allies to take the battle to the enemy unharried.",
				ImagePath = string.Empty,
				Specs = new Specs
				{
					HitPoints = 4,
					Melee = 2,
					Ballistics = 2,
					Armor = 4
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
