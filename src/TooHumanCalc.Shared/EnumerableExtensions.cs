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

		public static TValue TryGetValue<TKey, TValue>(this IDictionary<TKey, TValue> dict, TKey key,
			TValue defaultValue = default(TValue))
		{
			return dict != null && dict.TryGetValue(key, out TValue val)
				? val
				: defaultValue;
		}
	}
}
