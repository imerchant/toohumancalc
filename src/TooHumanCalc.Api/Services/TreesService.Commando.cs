using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<ClassTree> _commandoTree = new Lazy<ClassTree>(GetCommandoTree);
		public static ClassTree CommandoTree => _commandoTree.Value;

		private static ClassTree GetCommandoTree()
		{
			return new ClassTree
			{
				Name = "Commando",
				Description = "Favoring technological gadgetry and stand-off methods of warfare, the Commando specializes in the use of mines, counter-measures, demolitions, and rifles. Able to support his allies through long-range harrying tactics, the Commando is truly a force to be reckoned with.",
				ImagePath = string.Empty,
				Specs = new Specs
				{
					HitPoints = 2,
					Melee = 1,
					Ballistics = 5,
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
