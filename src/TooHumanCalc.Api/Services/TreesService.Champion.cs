using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<ClassTree> _championTree = new Lazy<ClassTree>(GetChampionTree);
		public static ClassTree ChampionTree => _championTree.Value;

		private static ClassTree GetChampionTree()
		{
			return new ClassTree
			{
				Name = "Champion",
				Description = "The Champion represents ODIN\'s divine force of retribution. A strong warrior able to deal out a wide variety of caustic force field and anti-gravity based effects, increasing the combat effectiveness of his allies. One-handed weapons are the Champion\'s chosen tools of combat.",
				ImagePath = string.Empty,
				Specs = new Specs
				{
					HitPoints = 3,
					Melee = 3,
					Ballistics = 3,
					Armor = 3
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
