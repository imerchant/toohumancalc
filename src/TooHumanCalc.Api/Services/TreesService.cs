using System;
using System.Collections.Generic;
using TooHumanCalc.Model;

namespace TooHumanCalc.Api.Services
{
	public static partial class TreesService
	{
		private static readonly Lazy<IReadOnlyDictionary<string, ClassTree>> _allTrees = new Lazy<IReadOnlyDictionary<string, ClassTree>>(GetAllTrees);
		public static IReadOnlyDictionary<string, ClassTree> ClassTrees => _allTrees.Value;

		private static IReadOnlyDictionary<string, ClassTree> GetAllTrees()
		{
			return new Dictionary<string, ClassTree>(StringComparer.OrdinalIgnoreCase)
			{
				{ "berserker", BerserkerTree },
				{ "defender", DefenderTree },
				{ "champion", ChampionTree },
				{ "commando", CommandoTree },
				{ "bioEngineer", BioEngineerTree }
			};
		}
	}
}
