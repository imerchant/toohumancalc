using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<ClassTree> _berserkerTree = new Lazy<ClassTree>(GetBerserkerTree);
		public static ClassTree BerserkerTree => _berserkerTree.Value;

		private static ClassTree GetBerserkerTree()
		{
			return new ClassTree
			{
				Name = "Berserker",
				Description = "The Berserker delights in the fury of close combat, forgoing defensive strategy in order to adopt all-out offense. Adopting a twin-blade fighting style and infused with the spirit of the bear, a Berserker will wade into battle for the glory of ODIN.",
				ImagePath = string.Empty,
				Specs = new Specs
				{
					HitPoints = 2,
					Melee = 5,
					Ballistics = 1,
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
