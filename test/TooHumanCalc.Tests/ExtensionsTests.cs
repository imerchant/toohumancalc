using System.Collections.Generic;
using FluentAssertions;
using TooHumanCalc.Shared;
using Xunit;

namespace TooHumanCalc.Tests
{
	public class ExtensionsTests
	{
		[Fact]
		public void IEnumerable_HasAny_ReturnsFalseOnEmptyCollection()
		{
			List<int> collection = null;
			collection.HasAny().Should().BeFalse();

			collection = new List<int>(0);
			collection.HasAny().Should().BeFalse();
		}
	}
}
