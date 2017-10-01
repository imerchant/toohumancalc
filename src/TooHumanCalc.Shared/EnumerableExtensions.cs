using System.Collections.Generic;
using System.Linq;

namespace TooHumanCalc.Shared
{
	public static class EnumerableExtensions
	{
		public static bool HasAny<T>(this IEnumerable<T> source)
		{
			return source?.Any() == true;
		}
	}
}
